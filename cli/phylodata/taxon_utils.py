import taxoniq

from phylodata.types import ClassificationEntry


def look_up_taxon_metadata(taxon_id: int) -> list[ClassificationEntry]:
    taxon = taxoniq.Taxon(taxon_id)
    return [
        ClassificationEntry(
            id=str(t.tax_id),
            scientific_name=t.scientific_name,
        )
        for t in taxon.ranked_lineage
    ]
