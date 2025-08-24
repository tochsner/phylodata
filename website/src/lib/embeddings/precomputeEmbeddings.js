import fs from 'fs';
import path from 'path';
import 'dotenv/config';

import { OpenAI } from 'openai';

const embedding_model = 'text-embedding-3-large';
const apiKey = process.env.OPEN_AI_KEY;

const openai = new OpenAI({ apiKey });

/**
 * @param {string} content
 */
export async function computeEmbedding(content) {
	const response = await openai.embeddings.create({
		model: embedding_model,
		input: content
	});
	return response.data[0].embedding;
}

async function main() {
	const modelsDir = 'src/lib/models';

	const modelsFiles = fs.readdirSync(modelsDir);
	const markdownFiles = modelsFiles.filter((file) => file.endsWith('.md'));

	console.log(`Found ${markdownFiles.length} markdown files`);

	const results = [];

	for (const fileName of markdownFiles) {
		try {
			console.log(`Processing ${fileName}...`);
			const filePath = path.join(modelsDir, fileName);
			const content = fs.readFileSync(filePath, 'utf-8');

			results.push({
				fileName,
				embedding: await computeEmbedding(content),
				modelName: embedding_model
			});

			console.log(`Successfully processed ${fileName}`);
		} catch (error) {
			console.error(`Error processing ${fileName}:`, error);
		}
	}

	const outputPath = path.join(modelsDir, 'embeddings.json');
	fs.writeFileSync(outputPath, JSON.stringify(results, null, 2));

	console.log(`Embeddings saved to ${outputPath}`);
}

main().catch((error) => {
	console.error('Error:', error);
	process.exit(1);
});
