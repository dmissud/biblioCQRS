#!/usr/bin/env python3
"""Lint des skills BA : python3 .agents/tools/lint_skills.py [--skills-dir DIR] [--only NAME]."""
import argparse
import re
import sys
from pathlib import Path

TOOLS_DIR = Path(__file__).parent
LOCAL_FORBIDDEN = TOOLS_DIR / "forbidden-terms.local.txt"
DEFAULT_CONVENTIONS = TOOLS_DIR / "conventions.md"
DEFAULT_SKILLS_DIR = Path(".agents/skills")

BUILTIN_FORBIDDEN = ["Claude", "Gemini", "Antigravity", "Codex", "run_workflow", "mcp__", "AskUserQuestion"]
REQUIRED_HEADINGS = ["## Entrées / Sorties", "## Exemple : information manquante", "## Pré-requis absents"]
MIN_SCENARIOS = 3


def parse_frontmatter(text):
    match = re.match(r"---\r?\n(.*?)\r?\n---(?:\r?\n|\Z)", text, re.DOTALL)
    if not match:
        return {}
    fields = {}
    for line in match.group(1).splitlines():
        key, sep, value = line.partition(":")
        if sep and not line.startswith((" ", "\t")):
            fields[key.strip()] = value.strip().strip("\"'")
    return fields


def lint_skill(skill_dir, conventions, forbidden):
    skill_dir = Path(skill_dir)
    skill_md = skill_dir / "SKILL.md"
    if not skill_md.is_file():
        return [f"{skill_dir.name}: SKILL.md introuvable"]
    text = skill_md.read_text(encoding="utf-8")
    violations = []

    fields = parse_frontmatter(text)
    if fields.get("name") != skill_dir.name:
        violations.append(f"name '{fields.get('name', '')}' différent du dossier '{skill_dir.name}'")
    description = fields.get("description", "")
    if not description.startswith("À utiliser"):
        violations.append("description vide ou ne commençant pas par « À utiliser »")

    if conventions not in text:
        violations.append("bloc de conventions absent ou modifié")
    lines = set(text.splitlines())
    for heading in REQUIRED_HEADINGS:
        if heading not in lines:
            violations.append(f"titre manquant : {heading}")

    lowered = text.lower()
    for term in BUILTIN_FORBIDDEN + list(forbidden):
        if term.lower() in lowered:
            violations.append(f"terme interdit : {term}")

    scenarios = skill_dir / "evals" / "scenarios.md"
    if not scenarios.is_file():
        violations.append("evals/scenarios.md introuvable")
    else:
        count = len(re.findall(r"^### Scénario", scenarios.read_text(encoding="utf-8"), re.MULTILINE))
        if count < MIN_SCENARIOS:
            violations.append(f"evals/scenarios.md : {count} titres '### Scénario' (minimum {MIN_SCENARIOS})")
    return violations


def load_local_forbidden():
    if not LOCAL_FORBIDDEN.is_file():
        print(f"Avertissement : {LOCAL_FORBIDDEN} absent, termes interdits locaux ignorés", file=sys.stderr)
        return []
    lines = LOCAL_FORBIDDEN.read_text(encoding="utf-8").splitlines()
    return [line.strip() for line in lines if line.strip()]


def main(argv):
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--skills-dir", type=Path, default=DEFAULT_SKILLS_DIR)
    parser.add_argument("--conventions", type=Path, default=DEFAULT_CONVENTIONS)
    parser.add_argument("--only", metavar="NAME")
    args = parser.parse_args(argv)

    if not args.conventions.is_file():
        print(f"Erreur : fichier de conventions introuvable : {args.conventions}", file=sys.stderr)
        return 2
    conventions = args.conventions.read_text(encoding="utf-8").strip()
    if not args.skills_dir.is_dir():
        print(f"Erreur : dossier des skills introuvable : {args.skills_dir}", file=sys.stderr)
        return 2
    forbidden = load_local_forbidden()

    if args.only:
        skill_dirs = [args.skills_dir / args.only]
    else:
        skill_dirs = sorted(p for p in args.skills_dir.iterdir() if p.is_dir())

    failed = False
    for skill_dir in skill_dirs:
        for violation in lint_skill(skill_dir, conventions, forbidden):
            failed = True
            print(f"{skill_dir.name}: {violation}")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
