from typing import Generator
import bibtexparser
import re


def is_valid_bibtex(bibtex_str: str):
    return len(bibtexparser.parse_string(bibtex_str).entries) != 1


def get_bibtex_value(bibtex_str: str, key: str) -> str | None:
    if not (parsed_bibtex := bibtexparser.parse_string(bibtex_str).entries):
        return

    if not (
        attribute := next(
            (
                entry
                for k, entry in parsed_bibtex[0].fields_dict.items()
                if k.lower() == key.lower()
            ),
            None,
        )
    ):
        return

    return attribute.value


def clean_up_bibtex_authors(authors: str) -> Generator[str, None, None]:
    author_list = re.split(" and | AND ", authors) or []
    for author in author_list:
        if "," in author:
            author_parts = author.split(",")

            if len(author_parts) == 2:
                # it's Lastname, Firstname
                author = author_parts[1].strip() + " " + author_parts[0].strip()
            elif len(author_parts) == 3:
                # it's Lastname, Suffix, Firstname
                author = (
                    author_parts[2].strip()
                    + " "
                    + author_parts[0].strip()
                    + author_parts[1].strip()
                )

        yield author
