import itertools
from io import BytesIO

from commonnexus import Nexus

from phylodata.errors import ValidationError
from phylodata.types import Trees


def parse_trees(file: BytesIO) -> Trees:
    lines = itertools.chain.from_iterable(
        (string_line.decode("utf-8") for string_line in file)
    )
    nexus = Nexus(lines)

    if not nexus.TREES or not nexus.TREES.trees:
        raise ValidationError("Invalid BEAST 2 trees file.")

    example_tree = nexus.TREES.trees[0]

    return Trees(
        number_of_trees=len(nexus.TREES.trees),
        number_of_tips=get_number_of_tips(example_tree.newick),
        ultrametric=is_ultrametric(example_tree.newick),
        time_tree=True,
        rooted=True,  # BEAST 2 always produces rooted trees
        ccd1_entropy=0,  # TODO
        tree_ess=0,  # TODO
        ccd0_map_tree="",  # TODO
        hipstr_tree="",  # TODO
        leaf_to_sample_map={},  # TODO
        average_root_age=get_average_root_age(nexus.TREES.trees),
    )


def get_number_of_tips(newick_node):
    if not newick_node._descendants:
        return 1
    else:
        return sum(map(get_number_of_tips, newick_node._descendants))


def is_ultrametric(newick_node):
    return len(set(get_tip_dates(newick_node, 0.0))) == 1


def get_tip_dates(newick_node, accumulated_height):
    if not newick_node._descendants:
        return [accumulated_height + newick_node.length]
    else:
        return [
            date
            for child in newick_node._descendants
            for date in get_tip_dates(child, accumulated_height + newick_node.length)
        ]


def get_average_root_age(trees):
    root_ages = []

    for tree in trees:
        tip_dates = get_tip_dates(tree.newick, 0.0)
        root_age = max(tip_dates) - tree.newick.length
        root_ages.append(root_age)

    return sum(root_ages) / len(root_ages)
