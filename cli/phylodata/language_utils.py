import csv
import re
from typing import TypedDict

from rapidfuzz import fuzz, process

from phylodata.types import ClassificationEntry


def clean_label(label: str) -> str:
    label = label.replace("sequence", "")
    label = label.replace("seq", "")
    return re.sub(r"[^A-Za-z ]+", " ", label).lower().strip()


class Language(TypedDict):
    language_id: str

    parent_id: str
    name: str


languages: list[Language] = []
id_to_language: dict[str, Language] = {}
cleaned_language_names: list[str] = []

with open("data/glottolog_languoid_5.2.csv") as csv_file:
    glottolog_languages = csv.reader(csv_file)

    for row in glottolog_languages:
        languages.append(
            {
                "language_id": row[0],
                "parent_id": row[2],
                "name": row[3],
            }
        )
        id_to_language[row[0]] = {
            "language_id": row[0],
            "parent_id": row[2],
            "name": row[3],
        }
        cleaned_language_names.append(clean_label(row[3]))


def fetch_language_metadata(language_label: str) -> list[ClassificationEntry] | None:
    match = process.extractOne(
        clean_label(language_label),
        cleaned_language_names,
        scorer=fuzz.WRatio,
        score_cutoff=90,
    )

    if not match:
        return None

    match_idx = match[2]
    matched_language = languages[match_idx]

    classification = [
        ClassificationEntry(
            id=matched_language["language_id"],
            scientific_name=matched_language["name"],
        )
    ]
    classification = extend_classification(classification)

    return classification


def extend_classification(
    classification: list[ClassificationEntry],
) -> list[ClassificationEntry]:
    highest_language = id_to_language[classification[-1].id]
    next_parent_id = highest_language["parent_id"]
    next_parent = id_to_language.get(next_parent_id)

    if next_parent:
        classification.append(
            ClassificationEntry(
                id=next_parent["language_id"],
                scientific_name=next_parent["name"],
            )
        )
        classification = extend_classification(classification)

    return classification
