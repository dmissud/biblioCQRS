import subprocess
import sys
from pathlib import Path

import pytest

import check_uc_links as m

SCRIPT = Path(__file__).with_name("check_uc_links.py")


def write_uc(domains, uc_id, scenarios=()):
    d = domains / "pret" / "use-cases"
    d.mkdir(parents=True, exist_ok=True)
    lines = [f"# {uc_id} : Emprunter un livre", "", "## Scénarios", ""]
    lines += [f"- {path} :: {name}" for path, name in scenarios]
    lines += ["", "## Notes", "- autre :: ignoré"]
    (d / f"{uc_id}.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


def write_feature(path, tag, name, keyword="Scenario", newline="\n"):
    path.parent.mkdir(parents=True, exist_ok=True)
    text = f"Feature: Prêt\n\n  {tag}\n  {keyword}: {name}\n    Given un livre\n"
    path.write_bytes(text.replace("\n", newline).encode("utf-8"))


@pytest.fixture
def repo(tmp_path, monkeypatch):
    monkeypatch.chdir(tmp_path)
    return tmp_path


def kinds(findings):
    return sorted((f.kind, f.uc_id) for f in findings)


def run(domains, features):
    return m.check(Path(domains), [Path(features)])


def test_clean_repository_has_no_findings(repo):
    write_uc(repo / "domains", "UC-PRET-03", [("features/pret.feature", "Emprunt")])
    write_feature(repo / "features" / "pret.feature", "@UC-PRET-03", "Emprunt")
    assert run("domains", "features") == []


def test_uc_without_tagged_scenario_is_reported(repo):
    write_uc(repo / "domains", "UC-PRET-03")
    (repo / "features").mkdir()
    assert kinds(run("domains", "features")) == [("UC_WITHOUT_SCENARIO", "UC-PRET-03")]


def test_tag_without_use_case_file_is_reported(repo):
    (repo / "domains").mkdir()
    write_feature(repo / "features" / "a.feature", "@UC-PRET-09", "X")
    assert kinds(run("domains", "features")) == [("SCENARIO_WITHOUT_UC", "UC-PRET-09")]


def test_listed_scenario_missing_in_code_is_a_broken_link(repo):
    write_uc(repo / "domains", "UC-PRET-03", [("features/a.feature", "Absent")])
    write_feature(repo / "features" / "a.feature", "@UC-PRET-03", "Présent")
    assert kinds(run("domains", "features")) == [("BROKEN_LINK", "UC-PRET-03")]


def test_listed_scenario_without_tag_is_a_broken_link(repo):
    write_uc(repo / "domains", "UC-PRET-03", [("features/a.feature", "Emprunt")])
    write_feature(repo / "features" / "a.feature", "@autre", "Emprunt")
    found = kinds(run("domains", "features"))
    assert ("BROKEN_LINK", "UC-PRET-03") in found


def test_tag_is_case_sensitive(repo):
    write_uc(repo / "domains", "UC-PRET-03")
    write_feature(repo / "features" / "a.feature", "@uc-pret-03", "X")
    found = kinds(run("domains", "features"))
    assert any(k == "SCENARIO_WITHOUT_UC" for k, _ in found)


def test_uc_id_with_wrong_digits_is_not_matched(repo):
    write_uc(repo / "domains", "UC-PRET-03")
    write_feature(repo / "features" / "a.feature", "@UC-PRET-3", "X")
    found = kinds(run("domains", "features"))
    assert ("SCENARIO_WITHOUT_UC", "UC-PRET-3") in found
    assert ("UC_WITHOUT_SCENARIO", "UC-PRET-03") in found


def test_french_keywords_and_crlf_and_scenario_outline(repo):
    write_uc(repo / "domains", "UC-PRET-03", [("features/a.feature", "Emprunt plan")])
    write_feature(repo / "features" / "a.feature", "@UC-PRET-03", "Emprunt plan",
                  keyword="Plan du scénario", newline="\r\n")
    assert run("domains", "features") == []


def test_cli_exit_codes(repo):
    write_uc(repo / "domains", "UC-PRET-03", [("features/a.feature", "Emprunt")])
    write_feature(repo / "features" / "a.feature", "@UC-PRET-03", "Emprunt")

    def cli(*args):
        return subprocess.run([sys.executable, str(SCRIPT), *args], capture_output=True,
                              text=True, cwd=repo)

    ok = cli("--domains", "domains", "--features", "features")
    assert (ok.returncode, ok.stdout) == (0, "")

    write_feature(repo / "features" / "b.feature", "@UC-PRET-09", "Y")
    ko = cli("--domains", "domains", "--features", "features")
    assert ko.returncode == 1
    assert ko.stdout.splitlines()[0].split("\t")[:2] == ["SCENARIO_WITHOUT_UC", "UC-PRET-09"]

    missing = cli("--domains", "nope", "--features", "features")
    assert missing.returncode == 2 and "nope" in missing.stderr
