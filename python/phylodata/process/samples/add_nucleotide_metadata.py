from collections import defaultdict

from loguru import logger

from phylodata.data_types import ClassificationEntry, DataType, Sample
from phylodata.errors import BlastError
from phylodata.process.samples.run_blast import extract_taxon_ids, run_blast
from phylodata.process.samples.sample_name_matching import is_match
from phylodata.process.samples.sequence_utils import (
    contains_non_placeholder_characters,
    split_into_subsequences,
)
from phylodata.process.samples.taxon_classification import look_up_taxon_classification

MAX_SEQ_LENGTH_CONSIDERED = 5000
MAX_NUM_SUBSEQUENCES_CONSIDERED = 4
NUM_BLAST_HITS_CONSIDERED = 5


def add_nucleotide_metadata(samples: list[Sample]) -> list[Sample]:
    """Tries to add metadata to all samples for which we have nucleotide
    sequences."""
    if not samples:
        return []

    sequences, sample_idx = _collect_nucleotide_sequences(samples)
    taxon_ids = _fetch_taxon_ids(sequences)
    taxon_id_classifications = _fetch_classifications(taxon_ids)
    _add_classifications_by_voting(
        samples, sample_idx, taxon_ids, taxon_id_classifications
    )

    return samples


def _collect_nucleotide_sequences(samples: list[Sample]) -> tuple[list[str], list[int]]:
    """Collects all nucleotide sequences from the given samples. If necessary,
    long sequences will split up into smaller subsequences."""
    nucleotide_sequences: list[str] = []
    nucleotide_sequence_idx: list[int] = []

    for sample_idx, sample in enumerate(samples):
        if sample.classification:
            # we already have found a classification for this sample
            continue

        for data in sample.sample_data:
            if data.type not in [
                DataType.RNA,
                DataType.DNA,
            ] or not contains_non_placeholder_characters(data.data):
                continue

            for subsequence in split_into_subsequences(
                data.data,
                MAX_SEQ_LENGTH_CONSIDERED,
                MAX_NUM_SUBSEQUENCES_CONSIDERED,
            ):
                nucleotide_sequences.append(subsequence)
                nucleotide_sequence_idx.append(sample_idx)

    return nucleotide_sequences, nucleotide_sequence_idx


def _fetch_taxon_ids(
    sequences: list[str],
) -> list[list[int | None]]:
    if not sequences:
        return []

    blast_params = {
        "CMD": "Put",
        "PROGRAM": "blastn",
        "MEGABLAST": True,
        "DATABASE": "core_nt",
        "HITLIST_SIZE": NUM_BLAST_HITS_CONSIDERED,
    }

    try:
        blast_results = run_blast(sequences, blast_params, MAX_SEQ_LENGTH_CONSIDERED)
    except BlastError:
        return [[None]] * len(sequences)

    return extract_taxon_ids(blast_results)


def _fetch_classifications(
    batch_taxon_ids: list[list[int | None]],
) -> dict[int, list[ClassificationEntry] | None]:
    taxon_ids = {
        taxon_id for taxon_ids in batch_taxon_ids for taxon_id in taxon_ids if taxon_id
    }

    classifications = {}
    for taxon_id in taxon_ids:
        try:
            classifications[taxon_id] = look_up_taxon_classification(taxon_id)
        except Exception as e:
            logger.error(f"Taxon look up faild: {e}")
            classifications[taxon_id] = None

    return classifications


def _add_classifications_by_voting(
    samples: list[Sample],
    batch_sample_idx: list[int],
    batch_taxon_ids: list[list[int | None]],
    taxon_id_classifications: dict[int, list[ClassificationEntry] | None],
):
    taxon_ids_by_sample_idx: dict[int, list[list[int | None]]] = defaultdict(list)

    for sample_idx, taxon_ids in zip(batch_sample_idx, batch_taxon_ids):
        taxon_ids_by_sample_idx[sample_idx].append(taxon_ids)

    for i, sample in enumerate(samples):
        taxon_ids = taxon_ids_by_sample_idx[i]

        # we first check if there is a perfect match based on the sample_id

        if classification := _get_perfect_name_match(
            taxon_ids, taxon_id_classifications, sample.sample_id
        ):
            sample.scientific_name = classification[0].scientific_name
            sample.common_name = classification[0].common_name
            sample.classification = classification
            continue

        # otherwise, we use the ranking of the top BLAST and perform a voting

        votes_per_taxon_id = defaultdict(int)
        for taxon_ids in taxon_ids_by_sample_idx[i]:
            for place, taxon_id in enumerate(taxon_ids):
                if taxon_id:
                    votes_per_taxon_id[taxon_id] += len(taxon_ids) - place

        best_taxon_id = max(votes_per_taxon_id.items(), key=lambda x: x[1])[0]
        if classification := taxon_id_classifications.get(best_taxon_id):
            sample.scientific_name = classification[0].scientific_name
            sample.common_name = classification[0].common_name
            sample.classification = classification


def _get_perfect_name_match(
    taxon_ids: list[list[int | None]],
    taxon_id_classifications: dict[int, list[ClassificationEntry] | None],
    sample_id: str,
) -> list[ClassificationEntry] | None:
    all_taxon_ids = {
        taxon_id for taxon_id_ranking in taxon_ids for taxon_id in taxon_id_ranking
    }

    for taxon_id in all_taxon_ids:
        if not taxon_id:
            continue

        if not (classification := taxon_id_classifications.get(taxon_id)):
            continue

        if is_match(sample_id, classification[0].scientific_name):
            return classification

        if is_match(sample_id, classification[0].common_name):
            return classification

    return None
