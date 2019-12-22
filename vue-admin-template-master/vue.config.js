module.exports = {

        publicPath: "/",
        // outputDir:'dist',
        // assetsDir: 'static',

        devServer: {
            port: 8099, // 端口号
            host: 'localhost',
            // proxy: {
            //     '/api': {
            //         target: 'http://localhost:8090', //对应自己的接口
            //         changeOrigin: true,
            //         ws: true,   // 是否启用websockets
            //         // pathRewrite: {
            //         //     '^/api': ''
            //         // }
            //     }
            // },
        },

  }
