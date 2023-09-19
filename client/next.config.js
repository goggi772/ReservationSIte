/** @type {import('next').NextConfig} */
const nextConfig = {
    async redirects() {
        return [
            {
                source: '/',
                destination: '/reservation',
                permanent: true,
            }
        ]
    },
}

module.exports = nextConfig
