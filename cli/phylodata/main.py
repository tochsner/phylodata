import subprocess


def main():
    subprocess.Popen(["streamlit", "run", "phylodata/ui.py"])
