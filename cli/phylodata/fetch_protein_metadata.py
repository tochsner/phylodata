import re
import requests
from phylodata.blast_utils import (
    BLAST_URL,
    build_fasta_data,
    wait_until_ready,
    fetch_results,
    extract_taxon_ids,
)
from phylodata.taxon_utils import look_up_taxon_metadata


MAX_SEQ_LENGTH_CONSIDERED = 160

def fetch_protein_metadata(
    sequences: list[str],
) -> list[tuple[str, list[str]] | None]:
    fasta_data = build_fasta_data(sequences, MAX_SEQ_LENGTH_CONSIDERED)

    request_id, _ = initiate_blast_request(fasta_data)
    wait_until_ready(request_id)

    blast_results = fetch_results(request_id)
    taxon_ids = extract_taxon_ids(blast_results)

    metadata = []

    for taxon_id in taxon_ids:
        if taxon_id:
            try:
                metadata.append(look_up_taxon_metadata(taxon_id))
            except Exception:
                metadata.append(None)
        else:
            metadata.append(None)

    return metadata


def initiate_blast_request(fasta_data: str) -> tuple[str, int]:
    params = {
        "CMD": "Put",
        "PROGRAM": "blastp",
        "DATABASE": "nr",
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
