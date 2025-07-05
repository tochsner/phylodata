from datetime import date

import streamlit as st

from phylodata.data_types import (
    EditableExperiment,
    ExperimentType,
    NonEditableExperiment,
)
from phylodata.module import Module


class ExperimentModule(Module[tuple[EditableExperiment, NonEditableExperiment]]):
    def ui(self):
        with st.container(border=True):
            st.write("""
            ### Experiment

            Optionally, enter some additional information about your experiment. This is useful if you want to register multiple experiments for the same paper.
            """)

            self.title = st.text_input("Experiment title (optional)")
            self.description = st.text_area("Experiment description (optional)")

    def validate(self): ...

    def parse(self) -> tuple[EditableExperiment, NonEditableExperiment]:
        return EditableExperiment(
            title=self.title.strip() or None,
            description=self.description.strip() or None,
            type=ExperimentType.BEAST2_Experiment,
        ), NonEditableExperiment(
            origin="manualUpload",
            upload_date=date.today(),
        )
