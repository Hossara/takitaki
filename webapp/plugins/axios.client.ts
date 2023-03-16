import axios from "axios"

export default defineNuxtPlugin(() =>
{
    axios.defaults.baseURL = useRuntimeConfig().public.BACKEND_URL

    return {
        provide: {
            axios: axios
        }
    }
})