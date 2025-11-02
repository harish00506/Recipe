/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#faf8f3',
          100: '#f5f1e8',
          200: '#ebe3d0',
          300: '#ddd0af',
          400: '#d4ba8e',
          500: '#c9a86d',
          600: '#b8935a',
          700: '#9a7847',
          800: '#7c633b',
          900: '#6a5230',
          950: '#3d2f1a',
        },
        accent: {
          50: '#fdf8f3',
          100: '#fdf2e8',
          200: '#fce5d1',
          300: '#fad4b9',
          400: '#f8c3a1',
          500: '#f5a962',
          600: '#f08c2e',
          700: '#dd7028',
          800: '#ba5820',
          900: '#97471b',
          950: '#54280d',
        },
      },
      fontFamily: {
        sans: ['Inter', 'Segoe UI', 'Roboto', 'sans-serif'],
        display: ['Poppins', 'sans-serif'],
        mono: ['Fira Code', 'monospace'],
      },
      spacing: {
        '128': '32rem',
      },
      animation: {
        'fade-in': 'fadeIn 0.3s ease-in-out',
        'slide-in': 'slideIn 0.3s ease-in-out',
        'bounce-slow': 'bounce 2s infinite',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideIn: {
          '0%': { transform: 'translateX(-10px)', opacity: '0' },
          '100%': { transform: 'translateX(0)', opacity: '1' },
        },
      },
      boxShadow: {
        'soft': '0 1px 3px 0 rgba(0, 0, 0, 0.1)',
        'medium': '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
        'lg-custom': '0 10px 25px -5px rgba(0, 0, 0, 0.1)',
      },
      borderRadius: {
        'xl-custom': '1rem',
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}
