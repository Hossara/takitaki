/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
      "./components/**/*.{js,vue,ts}",
      "./layouts/**/*.vue",
      "./pages/**/*.vue",
      "./plugins/**/*.{js,ts}",
      "./nuxt.config.{js,ts}",
      "./app.vue"
    ],
    theme: {
      extend: {
        fontFamily: {
          sf: ["SF", "sans-serif"]
        },
        colors: {
          "primary": "#1877f2",
          "secondary": "#42b72a"
        }
      },
    },
    plugins: [],
}