# PhyloData Website - Agent Guidelines

## Project Overview

The PhyloData Website is a modern SvelteKit-based web application that provides a user interface for the PhyloData repository for Bayesian Phylogenetics Experiments. The website enables researchers and method developers to browse, search, and interact with phylogenetic experiment data through an intuitive web interface, with features for data visualization, documentation, and experiment management.

## Project Structure

```
website/
├── src/
│   ├── lib/
│   │   ├── components/         # Reusable Svelte components
│   │   ├── docs/               # User-facing documentation (Markdown)
│   │   ├── db/                 # Database operations and Supabase integration
│   │   ├── embeddings/         # AI embeddings for search functionality
│   │   ├── icons/              # Custom SVG icon components
│   │   ├── models/             # BEAST model documentation and metadata
│   │   ├── schema/             # JSON schemas for validation
│   │   ├── services/           # External service integrations
│   │   ├── storage/            # File storage (Wasabi/S3) operations
│   │   ├── themes/             # CSS themes and styling
│   │   └── utils/              # Utility functions and helpers
│   ├── routes/                 # SvelteKit routes and pages
│   │   ├── api/                # API endpoints
│   │   ├── docs/               # Documentation pages
│   │   ├── experiments/        # Experiment browsing and details
│   │   ├── models/             # BEAST model documentation
│   │   └── new-experiment/     # Experiment creation interface
│   ├── app.html                # HTML template
│   ├── app.css                 # Global styles
│   └── app.d.ts                # TypeScript declarations
├── static/                     # Static assets
├── scripts/                    # Database and maintenance scripts
├── package.json                # Dependencies and scripts
├── svelte.config.js            # SvelteKit configuration
├── vite.config.ts              # Vite build configuration
├── tailwind.config.js          # Tailwind CSS configuration
└── tsconfig.json               # TypeScript configuration
```

## Development Guidelines

### Architecture Principles

- **Component-Based**: Build reusable Svelte components
- **Server-Side Rendering**: Leverage SvelteKit's SSR capabilities
- **Type Safety**: Use TypeScript for all code with proper type definitions
- **API Routes**: Create RESTful API endpoints for data operations
- **Accessibility**: Follow WCAG guidelines for inclusive design

### Technology Stack

- **Frontend**: SvelteKit, TypeScript, Tailwind CSS
- **Backend**: Supabase (PostgreSQL, Auth, Storage)
- **File Storage**: Wasabi (S3-compatible)
- **AI/ML**: OpenAI embeddings for search
- **Build Tools**: Vite, Prettier, ESLint
- **Deployment**: SvelteKit adapter-auto

## Best Practices for Agents

### When Working on This Project

1. **Understand the Domain**: Familiarize yourself with phylogenetic data structures and BEAST model terminology
2. **Follow SvelteKit Conventions**: Use proper routing, server-side rendering, and API patterns
3. **Component Design**: Create reusable, accessible components with proper TypeScript types
4. **Database Operations**: Use Supabase client properly with error handling
5. **Styling**: Follow Tailwind CSS patterns and maintain design consistency

### Code Quality Standards

- **TypeScript First**: Add comprehensive type definitions for all functions and components
- **Component Props**: Define clear prop interfaces for all Svelte components
- **Error Handling**: Implement proper error boundaries and user feedback
- **Performance**: Optimize for Core Web Vitals and loading performance
- **SEO**: Ensure proper meta tags and structured data for search engines
- **Accessibility**: Include ARIA labels, keyboard navigation, and screen reader support
