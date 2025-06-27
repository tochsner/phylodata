import re

import requests

from phylodata.blast_utils import (
    BLAST_URL,
    build_fasta_data,
    extract_taxon_ids,
    fetch_results,
    wait_until_ready,
)
from phylodata.taxon_utils import look_up_taxon_classification
from phylodata.types import ClassificationEntry, DataType, Sample, SampleType

MAX_SEQ_LENGTH_CONSIDERED = 500


def add_nucleotide_metadata(samples: list[Sample]) -> list[Sample]:
    """Tries to add metadata to all samples for which we have nucleotide
    sequences."""
    # collect all nucleotide sequences

    nucleotide_sequences: list[str] = []
    nucleotide_sequence_idx: list[int] = []

    for idx, sample in enumerate(samples):
        if sample.type != SampleType.UNKNOWN:
            continue

        for data in sample.data:
            if data.type in [DataType.RNA, DataType.DNA]:
                nucleotide_sequences.append("".join(data.data))
                nucleotide_sequence_idx.append(idx)
                break  # we only need one sequence per sample

    # fetch the nucleotide classifications

    classification = fetch_nucleotide_classifications(nucleotide_sequences)

    # decide on the tree type

    species = set([classification[0].scientific_name for classification in classification if classification])
    tree_type = SampleType.SPECIES if 1 < len(species) > 1 else SampleType.CELLS

    # add the classifications to the samples

    for idx, classification in zip(nucleotide_sequence_idx, classification):
        if classification:
            samples[idx].classification = classification
            samples[idx].scientific_name = classification[0].scientific_name
            samples[idx].type = tree_type

    return samples


def fetch_nucleotide_classifications(
    sequences: list[str],
) -> list[list[ClassificationEntry] | None]:
    fasta_data = build_fasta_data(sequences, MAX_SEQ_LENGTH_CONSIDERED)

    request_id, _ = initiate_blast_request(fasta_data)
    wait_until_ready(request_id)

    blast_results = fetch_results(request_id)
    taxon_ids = extract_taxon_ids(blast_results)

    classifications: list[list[ClassificationEntry] | None] = []

    for taxon_id in taxon_ids:
        if taxon_id:
            try:
                classifications.append(look_up_taxon_classification(taxon_id))
            except Exception:
                classifications.append(None)
        else:
            classifications.append(None)

    return classifications


def initiate_blast_request(fasta_data: str) -> tuple[str, int]:
    params = {
        "CMD": "Put",
        "PROGRAM": "blastn",
        "MEGABLAST": True,
        "DATABASE": "core_nt",
        "QUERY": fasta_data,
        "HITLIST_SIZE": 1,
    }

    response = requests.post(BLAST_URL, data=params)
    if response.status_code != 200:
        raise Exception(f"BLAST submission failed: {response.text}")

    request_id = re.search(r"RID = (\S+)", response.text)
    wait_time_s = re.search(r"RTOE = (\d+)", response.text)

    if not request_id or not wait_time_s:
        raise Exception("Failed to parse BLAST response")

    request_id = request_id.group(1)
    wait_time_s = int(wait_time_s.group(1))

    return request_id, wait_time_s
