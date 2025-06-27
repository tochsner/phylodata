import re
from time import sleep

import requests

BLAST_URL = "https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi"
WAIT_TIME_S = 5


def build_fasta_data(sequences: list[str], max_length_considered: int) -> str:
    fasta_data = ""

    for i, seq in enumerate(sequences):
        fasta_data += f">{i}\n{seq[:max_length_considered]}\n"

    return fasta_data


def wait_until_ready(request_id: str):
    result_params = {
        "CMD": "Get",
        "RID": request_id,
    }

    response = requests.get(BLAST_URL, params=result_params)
    status = re.findall("Status=(.*)", response.text)[0]

    match status:
        case "READY":
            return
        case "WAITING":
            sleep(WAIT_TIME_S)
            wait_until_ready(request_id)
        case _:
            raise Exception(f"BLAST submission failed: {status}")


def fetch_results(request_id: str) -> dict:
    result_params = {
        "CMD": "Get",
        "RID": request_id,
        "FORMAT_TYPE": "JSON2_S",
        "ALIGNMENTS": 1,
        "DESCRIPTIONS": 1,
    }

    response = requests.get(BLAST_URL, params=result_params)

    result = response.json()
    return result


def extract_taxon_ids(blast_json: dict) -> list[int | None]:
    taxon_ids = []

    for result in blast_json["BlastOutput2"]:
        try:
            taxon_id = result["report"]["results"]["search"]["hits"][0]["description"][
                0
            ]["taxid"]
            taxon_id = int(taxon_id)
        except:
            taxon_id = None

        taxon_ids.append(taxon_id)

    return taxon_ids
