CREATE VIEW "distinctEvolutionaryModelNames" AS
SELECT DISTINCT "name" FROM "evolutionaryModels"
WHERE "name" IS NOT NULL;
