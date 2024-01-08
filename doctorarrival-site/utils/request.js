const baseURL = 'http://localhost'

const token = useCookie('token')

export function request(url, options) {
    return useFetch(url, {
        baseURL,
        timeout: 5000,
        onRequest({ request, options }) {
            if (token.value) {
                request.headers = new Headers(request.headers)
                request.headers.set('Authorization', token.value)
            }
        },
        onResponse({ request, response, options }) {
            // USER_NOT_LOGIN(300, "用户未登录"),
            // TOKEN_EXPIRED(301, "token已过期"),
            const result = response._data
            if (result.code === 0) {
                response._data = result.data
            } else if (result.code === 300 || result.code === 301) {
                token.value = ''
                // 展示登录框
            } else {
                ElMessage({
                    message: result.message,
                    type: 'error'
                })
            }
        },
        ...options
    })
}
