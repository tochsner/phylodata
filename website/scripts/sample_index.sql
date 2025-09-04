CREATE VIEW "distinctSampleNames" AS
SELECT DISTINCT "commonName" AS name, "idType" FROM classifications
WHERE "commonName" IS NOT NULL

UNION

SELECT DISTINCT "scientificName" AS name, "idType" FROM classifications
WHERE "scientificName" IS NOT NULL;
