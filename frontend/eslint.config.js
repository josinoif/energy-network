import js from '@eslint/js'
import vue from 'eslint-plugin-vue'
import tseslint from '@typescript-eslint/eslint-plugin'
import tsParser from '@typescript-eslint/parser'

export default [
  js.configs.recommended,
  {
    languageOptions: {
      parser: tsParser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
        project: ['./tsconfig.json'],
        extraFileExtensions: ['.vue'],
      },
      globals: {
        defineProps: 'readonly',
        defineEmits: 'readonly',
        defineExpose: 'readonly',
        withDefaults: 'readonly'
      }
    },
    plugins: {
      vue,
      '@typescript-eslint': tseslint,
    },
    files: ['**/*.ts', '**/*.vue'],
    rules: {
      'vue/multi-word-component-names': 'off',
      // adicione mais regras se quiser
    }
  },
  {
    files: ['**/*.vue'],
    processor: vue.processors['.vue'],
  }
]
