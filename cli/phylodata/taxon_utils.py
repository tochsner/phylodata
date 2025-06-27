import taxoniq


def look_up_taxon_metadata(taxon_id: int) -> tuple[str, list[str]]:
    taxon = taxoniq.Taxon(taxon_id)
    return taxon.scientific_name, [t.scientific_name for t in taxon.ranked_lineage]
