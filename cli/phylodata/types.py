from datetime import date
from enum import Enum
from typing import Dict, List, Optional

import msgspec


class ExperimentType(Enum):
    BEAST2_Experiment = "beast2Experiment"


class FileType(Enum):
    BEAST2_CONFIGURATION = "beast2Configuration"
    BEAST2_POSTERIOR_LOGS = "beast2PosteriorLogs"
    BEAST2_POSTERIOR_TREES = "beast2PosteriorTrees"
    SUMMARY_TREE = "summaryTree"
    CODEPHY_MODEL = "codephyModel"
    EVO_DATA_EXPERIMENT = "evoDataExperiment"
    UNKNOWN = "unknown"


class SampleType(Enum):
    SPECIES = "species"
    CELL = "cell"
    LANGUAGE = "language"
    OTHER = "other"


class DataType(Enum):
    RNA = "rna"
    DNA = "dna"
    AMINO_ACIDS = "aminoAcids"
    PHASED_DIPLOID_DNA = "phasedDiploidDna"
    TRAITS = "traits"


class ModelType(Enum):
    SUBSTITUTION_MODEL = "substitutionModel"
    CLOCK_MODEL = "clockModel"
    TREE_PRIOR = "treePrior"
    TREE_LIKELIHOOD = "treeLikelihood"
    OTHER = "other"


class Experiment(msgspec.Struct, rename="camel"):
    type: ExperimentType
    origin: str
    upload_date: date
    title: Optional[str] = None
    description: Optional[str] = None
    license: str = "CC0"
    id: Optional[str] = None


class Paper(msgspec.Struct, rename="camel"):
    title: str
    authors: List[str]
    abstract: str
    bibtex: str
    doi: Optional[str] = None
    id: Optional[str] = None
    url: Optional[str] = None


class File(msgspec.Struct, rename="camel"):
    name: str
    type: FileType
    version: int
    size_bytes: int
    md5: str
    local_path: Optional[str] = None
    remote_path: Optional[str] = None


class SampleData(msgspec.Struct, rename="camel"):
    type: DataType
    length: int
    data: str


class Sample(msgspec.Struct, rename="camel"):
    id: str
    scientific_name: str
    type: SampleType
    classification: Dict[str, str]
    data: List[SampleData]


class Trees(msgspec.Struct, rename="camel"):
    number_of_trees: int
    number_of_tips: int
    ultrametric: bool
    rooted: bool
    ccd1_entropy: float
    tree_ess: int
    ccd0_map_tree: str
    hipstr_tree: str
    leaf_to_sample_map: Dict[str, str]
    average_root_age_years: float


class EvolutionaryModelComponent(msgspec.Struct, rename="camel"):
    name: str
    type: ModelType
    documentation_url: str


class EvolutionaryModel(msgspec.Struct, rename="camel"):
    models: List[EvolutionaryModelComponent]


class Metadata(msgspec.Struct, rename="camel"):
    evo_data_pipeline_version: str


class PaperWithExperiment(msgspec.Struct, rename="camel"):
    experiment: Experiment
    paper: Paper
    files: List[File]
    samples: List[Sample]
    trees: Trees
    evolutionary_model: EvolutionaryModel
    metadata: Metadata
