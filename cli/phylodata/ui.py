from enum import Enum
import streamlit as st
import bibtexparser
from datetime import date
from phylodata.parse_evolutionary_model import parse_evolutionary_model
from phylodata.version import __version__

from phylodata.types import (
    Experiment,
    ExperimentType,
    FileType,
    Metadata,
    Paper,
)
from phylodata.errors import ValidationError
from phylodata.parse_files import parse_file
from phylodata.types import PaperWithExperiment
from phylodata.parse_samples import parse_samples
from phylodata.parse_trees import parse_trees


class Stage(str, Enum):
    INPUT = "Input"
    VALIDATION = "Validation"
    NEXT_STEPS = "Next Steps"


STAGE = "stage"

if STAGE not in st.session_state:
    st.session_state[STAGE] = Stage.INPUT


if st.session_state[STAGE] == Stage.INPUT:
    """
    # Process Experiment

    Let's process your Bayesian Phylogenetics experiment to prepare it for upload to the PhyloData repository.

    Note that right now, we only support BEAST 2 experiments.

    This usually takes less than five minutes.
    """

    with st.container(border=True):
        """
        ### Paper

        Enter the following general information about your paper:
        """

        title = st.text_input("Title")
        abstract = st.text_area("Abstract")
        authors = st.text_area("Authors (one per line)")
        bibtext = st.text_area("BibTex citation")
        doi = st.text_input("DOI (optional)")
        url = st.text_input("URL (optional)")

    with st.container(border=True):
        """
        ### Experiment

        Optionally, enter some additional information about your experiment. This is useful if you want to register multiple experiments for the same paper.
        """

        experiment_title = st.text_input("Experiment title (optional)")
        experiment_description = st.text_area("Experiment description (optional)")

    with st.container(border=True):
        """
        ### Files

        Select the following files:
        """

        beast2_configuration = st.file_uploader(
            "BEAST 2 XML configuration", type=["xml"]
        )
        beast2_logs = st.file_uploader("BEAST 2 log file", type=["log"])
        beast2_trees = st.file_uploader("BEAST 2 posterior trees", type=["trees"])

        """
        Optionally, you can also select other files (like summary trees) which you consider an important part of your analysis.
        """

        other_files = st.file_uploader(
            "Other files (optional)", type=["*"], accept_multiple_files=True
        )

    if beast2_configuration:
        print(beast2_configuration.name)
        print(beast2_configuration.file_id)

    """
    Almost done!
    """

    def process_input():
        if not title.strip():
            st.toast("Specify a paper title.")
            return
        if not abstract.strip():
            st.toast("Specify a paper abstract.")
            return
        if not authors.strip():
            st.toast("Specify at least one paper author.")
            return
        if len(bibtexparser.parse_string(bibtext).entries) != 1:
            st.toast("Specify exactly one bibtex entry.")
            return
        if not beast2_configuration:
            st.toast("Specify a BEAST 2 configuration file.")
            return
        if not beast2_logs:
            st.toast("Specify a BEAST 2 log file.")
            return
        if not beast2_trees:
            st.toast("Specify a BEAST 2 trees file.")
            return

        with st.spinner("Processing your analysis..."):
            paper = Paper(
                title=title.strip(),
                authors=[author.strip() for author in authors.split("\n")],
                abstract=abstract.strip(),
                bibtex=bibtext.strip(),
                doi=doi.strip() or None,
                url=url.strip() or None,
            )

            experiment = Experiment(
                title=experiment_title.strip() or None,
                description=experiment_description.strip() or None,
                origin="manualUpload",
                upload_date=date.today(),
                type=ExperimentType.BEAST2_Experiment,
            )

            metadata = Metadata(evo_data_pipeline_version=__version__)

            try:
                files = []
                files.append(
                    parse_file(beast2_configuration, FileType.BEAST2_CONFIGURATION)
                )
                files.append(parse_file(beast2_logs, FileType.BEAST2_POSTERIOR_LOGS))
                files.append(parse_file(beast2_trees, FileType.BEAST2_POSTERIOR_TREES))
                for other_file in other_files:
                    files.append(parse_file(other_file, FileType.UNKNOWN))

                samples = parse_samples(beast2_configuration)
                trees = parse_trees(beast2_trees)
                evolutionary_model = parse_evolutionary_model(beast2_configuration)
            except ValidationError as error:
                st.toast(error.message)
                return

            paper_with_experiment = PaperWithExperiment(
                experiment=experiment,
                paper=paper,
                files=files,
                samples=samples,
                trees=trees,
                evolutionary_model=evolutionary_model,
                metadata=metadata,
            )

    process_experiment = st.button("Process experiment", type="primary")
    if process_experiment:
        process_input()
