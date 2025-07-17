CREATE VIEW "distinctSampleNames" AS
SELECT DISTINCT "commonName" AS name FROM classifications
WHERE "commonName" IS NOT NULL

UNION

SELECT DISTINCT "scientificName" AS name FROM classifications
WHERE "scientificName" IS NOT NULL;
