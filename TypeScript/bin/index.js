#!/usr/bin/env node
const fs = require('fs')
const path = require('path')
const mkdirp = require('mkdirp')
const glob = require('glob')
const {gql2ts} = require('../dist/index.js');

const args = process.argv.slice(2);

const input = args[0]
const output = args[1]

console.log("Generate typescript files:")
console.log(`  input: ${input}`)
console.log(`  output: ${output}`)

const opts = {}

glob(input, opts, function (er, files) {
  mkdirp.sync(output, opts)
  const res = files
    .filter(file => file.endsWith(".graphql") || file.endsWith(".graphqls"))
    .map(file => {
      const base = path.basename(file)
      const schema = fs.readFileSync(file, 'utf8');
      const typescript = gql2ts(schema)
      const writeFile = path.resolve(output, base.replace('.graphqls', '.ts').replace('.graphql', '.ts'))
      fs.writeFileSync(writeFile, typescript)
    })
  console.log(`${res.length} files successfully generated`)
})
