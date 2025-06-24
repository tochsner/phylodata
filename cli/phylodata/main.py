import argparse
from phylodata.ui import show


parser = argparse.ArgumentParser(description="My CLI tool")
parser.add_argument("--config", action="store_true", help="Show config")


def main():
    args = parser.parse_args()
    print(args.config)

    show()


if __name__ == "__main__":
    main()
