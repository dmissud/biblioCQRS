import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

import lint_skills
from lint_skills import lint_skill, main

CONVENTIONS = "## Conventions\n\nRègle commune A.\nRègle commune B."


def make_skill(root, name="demo", description="À utiliser pour tester.", body=None,
               front_name=None, scenarios=3):
    skill = root / name
    (skill / "evals").mkdir(parents=True)
    if body is None:
        body = (
            f"{CONVENTIONS}\n\n## Entrées / Sorties\n\nx\n\n"
            "## Exemple : information manquante\n\nx\n\n## Pré-requis absents\n\nx\n"
        )
    front = f"---\nname: {front_name or name}\ndescription: {description}\n---\n\n"
    (skill / "SKILL.md").write_text(front + body, encoding="utf-8")
    (skill / "evals" / "scenarios.md").write_text(
        "# Scénarios\n\n" + "".join(f"### Scénario {i}\n\nx\n\n" for i in range(scenarios)),
        encoding="utf-8",
    )
    return skill


def test_conforming_skill_has_no_violations(tmp_path):
    assert lint_skill(make_skill(tmp_path), CONVENTIONS, []) == []


def test_name_must_match_folder(tmp_path):
    skill = make_skill(tmp_path, front_name="autre")
    assert any("name" in v for v in lint_skill(skill, CONVENTIONS, []))


def test_description_must_start_with_a_utiliser(tmp_path):
    skill = make_skill(tmp_path, description="Sert à tester.")
    assert any("description" in v for v in lint_skill(skill, CONVENTIONS, []))


def test_missing_conventions_block_is_reported(tmp_path):
    body = "## Entrées / Sorties\n\n## Exemple : information manquante\n\n## Pré-requis absents\n"
    skill = make_skill(tmp_path, body=body)
    assert any("conventions" in v.lower() for v in lint_skill(skill, CONVENTIONS, []))


def test_modified_conventions_block_is_reported(tmp_path):
    skill = make_skill(tmp_path)
    md = skill / "SKILL.md"
    md.write_text(md.read_text(encoding="utf-8").replace("commune B", "commune C"), encoding="utf-8")
    assert any("conventions" in v.lower() for v in lint_skill(skill, CONVENTIONS, []))


def test_missing_required_headings_are_reported(tmp_path):
    skill = make_skill(tmp_path, body=CONVENTIONS + "\n")
    violations = lint_skill(skill, CONVENTIONS, [])
    for heading in ("## Entrées / Sorties", "## Exemple : information manquante", "## Pré-requis absents"):
        assert any(heading in v for v in violations), heading


def test_forbidden_term_is_reported(tmp_path):
    skill = make_skill(tmp_path)
    md = skill / "SKILL.md"
    md.write_text(md.read_text(encoding="utf-8") + "\nUtilise claude ici.\nEt AcmeCorp.\n", encoding="utf-8")
    violations = lint_skill(skill, CONVENTIONS, ["acmecorp"])
    assert any("Claude" in v for v in violations)
    assert any("acmecorp" in v for v in violations)


def test_evals_need_three_scenarios(tmp_path):
    skill = make_skill(tmp_path, scenarios=2)
    assert any("Scénario" in v for v in lint_skill(skill, CONVENTIONS, []))
    (skill / "evals" / "scenarios.md").unlink()
    assert any("scenarios.md" in v for v in lint_skill(skill, CONVENTIONS, []))


def _setup(tmp_path, **kw):
    skills = tmp_path / "skills"
    skills.mkdir()
    make_skill(skills, **kw)
    conv = tmp_path / "conventions.md"
    conv.write_text(CONVENTIONS, encoding="utf-8")
    return skills, conv


def test_main_returns_1_on_violation_and_0_when_clean(tmp_path):
    skills, conv = _setup(tmp_path)
    args = ["--skills-dir", str(skills), "--conventions", str(conv)]
    assert main(args) == 0
    make_skill(skills, name="bad", description="Nope.")
    assert main(args) == 1


def test_missing_local_forbidden_file_only_warns(tmp_path, capsys, monkeypatch):
    skills, conv = _setup(tmp_path)
    monkeypatch.setattr(lint_skills, "LOCAL_FORBIDDEN", tmp_path / "absent.txt")
    assert main(["--skills-dir", str(skills), "--conventions", str(conv)]) == 0
    assert "absent.txt" in capsys.readouterr().err


def test_missing_conventions_file_returns_2(tmp_path, capsys):
    skills, _ = _setup(tmp_path)
    assert main(["--skills-dir", str(skills), "--conventions", str(tmp_path / "nope.md")]) == 2
    assert capsys.readouterr().err


def test_only_flag_lints_a_single_skill(tmp_path):
    skills, conv = _setup(tmp_path)
    make_skill(skills, name="bad", description="Nope.")
    base = ["--skills-dir", str(skills), "--conventions", str(conv)]
    assert main(base + ["--only", "demo"]) == 0
    assert main(base + ["--only", "bad"]) == 1
    assert main(base + ["--only", "missing"]) == 1


def test_missing_skills_dir_returns_2(tmp_path, capsys):
    _, conv = _setup(tmp_path)
    assert main(["--skills-dir", str(tmp_path / "nope"), "--conventions", str(conv)]) == 2
    assert "nope" in capsys.readouterr().err


def test_frontmatter_closing_line_without_trailing_newline_is_parsed():
    assert lint_skills.parse_frontmatter("---\nname: demo\ndescription: À utiliser x\n---") == {
        "name": "demo", "description": "À utiliser x"}


def test_frontmatter_only_skill_md_has_no_name_or_description_violation(tmp_path):
    skill = make_skill(tmp_path)
    (skill / "SKILL.md").write_text("---\nname: demo\ndescription: À utiliser pour tester.\n---", encoding="utf-8")
    violations = lint_skill(skill, CONVENTIONS, [])
    assert not any(v.startswith("name") or v.startswith("description") for v in violations)


def test_local_forbidden_file_is_loaded_by_main(tmp_path, monkeypatch):
    skills, conv = _setup(tmp_path)
    md = skills / "demo" / "SKILL.md"
    md.write_text(md.read_text(encoding="utf-8") + "\nParle de ZorgLub ici.\n", encoding="utf-8")
    local = tmp_path / "local.txt"
    local.write_text("\n  zorglub  \n\n", encoding="utf-8")
    monkeypatch.setattr(lint_skills, "LOCAL_FORBIDDEN", local)
    assert main(["--skills-dir", str(skills), "--conventions", str(conv)]) == 1


def test_missing_skill_md_is_a_violation(tmp_path):
    skill = tmp_path / "empty"
    skill.mkdir()
    assert lint_skill(skill, CONVENTIONS, []) == ["empty: SKILL.md introuvable"]


def test_skill_md_without_frontmatter_reports_violations(tmp_path):
    skill = make_skill(tmp_path)
    (skill / "SKILL.md").write_text(CONVENTIONS + "\n", encoding="utf-8")
    violations = lint_skill(skill, CONVENTIONS, [])
    assert any(v.startswith("name") for v in violations)
    assert any("description" in v for v in violations)


def test_forbidden_terms_in_evals_are_reported(tmp_path):
    skill = make_skill(tmp_path)
    scenarios = skill / "evals" / "scenarios.md"
    scenarios.write_text(scenarios.read_text(encoding="utf-8") + "\nUn zorglub et Gemini.\n", encoding="utf-8")
    violations = lint_skill(skill, CONVENTIONS, ["zorglub"])
    assert any("evals/scenarios.md" in v and "zorglub" in v for v in violations)
    assert any("evals/scenarios.md" in v and "Gemini" in v for v in violations)
