import taxoniq

from phylodata.data_types import ClassificationEntry, ClassificationEntryType


def look_up_taxon_classification(ncbi_taxon_id: int) -> list[ClassificationEntry]:
    """Returns the classification of the specified NCBI taxon."""
    taxon = taxoniq.Taxon(ncbi_taxon_id)
    return [
        ClassificationEntry(
            classification_id=str(t.tax_id),
            scientific_name=t.scientific_name,
            common_name=t.common_name,
            id_type=ClassificationEntryType.NCBI_TAXONOMY_ID,
        )
        for t in taxon.ranked_lineage
    ]
