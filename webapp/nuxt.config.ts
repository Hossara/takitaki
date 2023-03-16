// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    css: [
        '@/assets/css/main.css',
    ],
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        }
    },
    runtimeConfig: {
        public: {
            BACKEND_URL: process.env.BACKEND_URL
        }
    }
})
