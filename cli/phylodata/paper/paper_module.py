from datetime import datetime
import bibtexparser
import streamlit as st

from phylodata.data_types import EditablePaper, NonEditablePaper
from phylodata.errors import ValidationError
from phylodata.module import Module
from phylodata.paper.doi import is_doi
from phylodata.paper.paper_id import generate_paper_id


class PaperModule(Module[tuple[EditablePaper, NonEditablePaper]]):
    def ui(self):
        with st.container(border=True):
            st.write("""
            ### Paper

            Enter the following general information about your paper:
            """)

            self.title = st.text_input("Title")
            self.year = st.number_input("Publication year", step=1)
            self.abstract = st.text_area("Abstract")
            self.authors = st.text_area("Authors")
            st.markdown(
                """One author per line. Use *Name Surname* like *Joseph Felsenstein*."""
            )
            self.bibtext = st.text_area("BibTex citation")
            self.doi = st.text_input("DOI")
            st.markdown(
                """Use the URL format (like *https://doi.org/10.1093/sysbio/22.3.240*).
                Experiments with the same paper DOI will be linked to the same paper."""
            )
            self.url = st.text_input("URL (optional)")

    def validate(self):
        if not self.title.strip():
            raise ValidationError("Specify a paper title.")
        if not self.year or self.year < 1800 or datetime.now().year + 1 < self.year:
            raise ValidationError("Specify a valid paper publication year.")
        if not self.abstract.strip():
            raise ValidationError("Specify a paper abstract.")
        if not self.authors.strip():
            raise ValidationError("Specify at least one paper author.")
        if len(bibtexparser.parse_string(self.bibtext).entries) != 1:
            st.toast("Specify exactly one bibtex entry.")
            return
        if not is_doi(self.doi.strip()):
            st.toast("Specify a DOI (like https://doi.org/10.1000/182).")
            return

    def parse(self) -> tuple[EditablePaper, NonEditablePaper]:
        return EditablePaper(
            title=self.title.strip(),
            year=self.year,
            authors=[author.strip() for author in self.authors.split("\n")],
            abstract=self.abstract.strip(),
            bibtex=self.bibtext.strip(),
            url=self.url.strip() or None,
        ), NonEditablePaper(
            doi=self.doi.strip(),
            human_readable_id=generate_paper_id(
                self.title.strip(),
                self.year,
                [author.strip() for author in self.authors.split("\n")],
            ),
        )
