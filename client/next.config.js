/** @type {import('next').NextConfig} */
const nextConfig = {
    async redirects() {
        return [
            {
                source: '/',
                destination: '/admin/reservation',
                permanent: true,
            }
        ]
    },
    output: 'standalone'
}

module.exports = nextConfig
