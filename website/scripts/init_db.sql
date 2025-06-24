-- Create papers table
CREATE TABLE papers (
   id SERIAL PRIMARY KEY,
   title TEXT NOT NULL,
   authors TEXT[] NOT NULL,
   abstract TEXT NOT NULL,
   bibtex TEXT NOT NULL,
   doi TEXT NOT NULL,
   url TEXT
);

-- Create experiments table
CREATE TABLE experiments (
   id SERIAL PRIMARY KEY,
   paper_id integer NOT NULL,
   title TEXT NOT NULL,
   origin TEXT NOT NULL,
   doi TEXT NOT NULL,
   upload_date DATE NOT NULL,
   license VARCHAR(10) NOT NULL CHECK (license = 'CC0'),
   number_of_trees INTEGER NOT NULL,
   number_of_tips INTEGER NOT NULL,
   ultrametric BOOLEAN NOT NULL,
   rooted BOOLEAN NOT NULL,
   ccd1_entropy NUMERIC NOT NULL,
   tree_ess INTEGER NOT NULL,
   ccd0_map_tree TEXT NOT NULL,
   hipstr_tree TEXT NOT NULL,
   average_root_age_years NUMERIC NOT NULL,
   leaf_to_sample_map JSONB NOT NULL,
   pipeline_version TEXT NOT NULL,
   pipeline_hash TEXT NOT NULL,
   FOREIGN KEY (paper_id) REFERENCES papers(id) ON DELETE CASCADE
);

-- Create files table
CREATE TABLE files (
   id SERIAL PRIMARY KEY,
   experiment_id integer NOT NULL,
   name TEXT NOT NULL,
   type VARCHAR(50) NOT NULL CHECK (type IN (
       'beast2Configuration',
       'beast2PosteriorLogs',
       'beast2PosteriorTrees',
       'summaryTree',
       'codephyModel',
       'evoDataExperiment'
   )),
   version INTEGER NOT NULL,
   size_bytes BIGINT NOT NULL,
   md5 VARCHAR(32) NOT NULL,
   FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE
);

-- Create samples table
CREATE TABLE samples (
   id VARCHAR(255) PRIMARY KEY,
   experiment_id integer NOT NULL,
   scientific_name TEXT NOT NULL,
   type VARCHAR(20) NOT NULL CHECK (type IN ('species', 'cell', 'language', 'other')),
   classification JSONB NOT NULL,
   data JSONB NOT NULL,
   FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE
);

-- Create evolutionary_models table
CREATE TABLE evolutionary_models (
   id SERIAL PRIMARY KEY,
   experiment_id integer NOT NULL,
   name TEXT NOT NULL,
   model_type VARCHAR(50) NOT NULL CHECK (model_type IN (
       'substitutionModel',
       'clockModel',
       'treePrior',
       'treeLikelihood',
       'other'
   )),
   documentation_url TEXT NOT NULL,
   FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE
);
