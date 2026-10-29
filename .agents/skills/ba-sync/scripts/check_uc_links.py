#!/usr/bin/env python3
"""Contrôle la cohérence entre cas d'utilisation (markdown) et scénarios Gherkin taggés."""
import argparse
import os
import re
import sys
from pathlib import Path
from typing import NamedTuple

UC_HEADING = re.compile(r"^#\s+(UC-[A-Z0-9]+-[0-9]{2})\s*:")
LISTED = re.compile(r"^\s*[-*]\s+(.+?)\s+::\s+(.+?)\s*$")
HEADER = re.compile(r"^\s*([^\s:@#][^:]*?)\s*:(.*)$")
# Mots-clés portant un tag sans être un scénario.
NOT_SCENARIO = {
    "feature", "fonctionnalité", "functionality", "background", "contexte",
    "rule", "règle", "examples", "exemples", "scenarios", "scénarios",
}


class Finding(NamedTuple):
    kind: str
    uc_id: str
    detail: str


def _norm(path) -> str:
    return os.path.normpath(str(path)).replace(os.sep, "/")


def _read_lines(path: Path) -> list[str]:
    return path.read_text(encoding="utf-8-sig").replace("\r\n", "\n").replace("\r", "\n").split("\n")


def scan_use_cases(domains_dir: Path) -> dict[str, set[tuple[str, str]]]:
    result: dict[str, set[tuple[str, str]]] = {}
    for path in sorted(Path(domains_dir).glob("*/use-cases/UC-*.md")):
        uc_id, in_scenarios, listed = path.stem, False, set()
        for line in _read_lines(path):
            heading = UC_HEADING.match(line)
            if heading:
                uc_id = heading.group(1)
            elif line.startswith("## "):
                in_scenarios = line[3:].strip().lower() in ("scénarios", "scenarios")
            elif in_scenarios and (item := LISTED.match(line)):
                listed.add((_norm(item.group(1)), item.group(2)))
        result.setdefault(uc_id, set()).update(listed)
    return result


def _uc_tokens(line: str) -> list[str]:
    """Tokens `@UC-…` (casse ignorée sur le préfixe) d'une ligne de tags, sans le `@`."""
    return [t[1:] for t in line.split() if t.lower().startswith("@uc-")]


def scan_feature_tags(roots: list[Path]) -> dict[str, set[tuple[str, str]]]:
    result: dict[str, set[tuple[str, str]]] = {}
    for root in roots:
        for path in sorted(Path(root).rglob("*.feature")):
            tags: list[str] = []
            for line in _read_lines(path):
                stripped = line.strip()
                if not stripped or stripped.startswith("#"):
                    continue
                if stripped.startswith("@"):
                    tags += _uc_tokens(stripped)
                    continue
                header = HEADER.match(line)
                if tags and header and header.group(1).lower() not in NOT_SCENARIO:
                    for tag in tags:
                        result.setdefault(tag, set()).add((_norm(path), header.group(2).strip()))
                tags = []
    return result


def check(domains_dir: Path, feature_roots: list[Path]) -> list[Finding]:
    use_cases = scan_use_cases(domains_dir)
    tagged = scan_feature_tags(feature_roots)
    findings: list[Finding] = []
    for uc_id in sorted(use_cases):
        if not tagged.get(uc_id):
            findings.append(Finding("UC_WITHOUT_SCENARIO", uc_id, "aucun scénario taggé dans le code"))
        for path, name in sorted(use_cases[uc_id] - tagged.get(uc_id, set())):
            findings.append(Finding("BROKEN_LINK", uc_id, f"{path} :: {name}"))
    for uc_id in sorted(set(tagged) - set(use_cases)):
        for path, name in sorted(tagged[uc_id]):
            findings.append(Finding("SCENARIO_WITHOUT_UC", uc_id, f"{path} :: {name}"))
    return findings


def main(argv=None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--domains", required=True, type=Path)
    parser.add_argument("--features", required=True, nargs="+", type=Path)
    args = parser.parse_args(argv)
    if not args.domains.is_dir():
        print(f"Dossier --domains introuvable : {args.domains}", file=sys.stderr)
        return 2
    for features in args.features:
        if not features.is_dir():
            print(f"Dossier --features introuvable : {features}", file=sys.stderr)
            return 2
    findings = check(args.domains, args.features)
    for finding in findings:
        print("\t".join(finding))
    return 1 if findings else 0


if __name__ == "__main__":
    sys.exit(main())
