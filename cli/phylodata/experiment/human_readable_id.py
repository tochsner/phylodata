import re

import nanoid


def generate_human_readable_id(title: str, year: int, authors: list[str]) -> str:
    """Generates a human-readable ID for the paper. Multiple calls with the same
    metadata will generate different IDs, as a random suffix is added.

    It is designed to be both recognizable and sufficiently random to avoid collisions.

    It generates IDs like 'felsenstein-1973-maximum-dsa6'."""
    author = replace_non_alpha(authors[0]).split()[-1].lower()
    title_excerpt = next(
        (
            word.lower()
            for word in replace_non_alpha(title).split()
            if word.lower() not in STOP_WORDS
        ),
        "",
    )
    random_id = nanoid.generate(size=4, alphabet="1234567890abcdefghijklmnopqrstuvwxyz")
    return f"{author}-{year}-{title_excerpt}-{random_id}"


def replace_non_alpha(text: str) -> str:
    """Replaces all non-alphabetical characters with a blank."""
    return re.sub("[^0-9a-zA-Z]+", " ", text)


STOP_WORDS = [
    "i",
    "me",
    "my",
    "myself",
    "we",
    "our",
    "ours",
    "ourselves",
    "you",
    "your",
    "yours",
    "yourself",
    "yourselves",
    "he",
    "him",
    "his",
    "himself",
    "she",
    "her",
    "hers",
    "herself",
    "it",
    "its",
    "itself",
    "they",
    "them",
    "their",
    "theirs",
    "themselves",
    "what",
    "which",
    "who",
    "whom",
    "this",
    "that",
    "these",
    "those",
    "am",
    "is",
    "are",
    "was",
    "were",
    "be",
    "been",
    "being",
    "have",
    "has",
    "had",
    "having",
    "do",
    "does",
    "did",
    "doing",
    "a",
    "an",
    "the",
    "and",
    "but",
    "if",
    "or",
    "because",
    "as",
    "until",
    "while",
    "of",
    "at",
    "by",
    "for",
    "with",
    "about",
    "against",
    "between",
    "into",
    "through",
    "during",
    "before",
    "after",
    "above",
    "below",
    "to",
    "from",
    "up",
    "down",
    "in",
    "out",
    "on",
    "off",
    "over",
    "under",
    "again",
    "further",
    "then",
    "once",
    "here",
    "there",
    "when",
    "where",
    "why",
    "how",
    "all",
    "any",
    "both",
    "each",
    "few",
    "more",
    "most",
    "other",
    "some",
    "such",
    "no",
    "nor",
    "not",
    "only",
    "own",
    "same",
    "so",
    "than",
    "too",
    "very",
    "s",
    "t",
    "can",
    "will",
    "just",
    "don",
    "should",
    "now",
]
"""The list of stop words taken from NLTK."""
