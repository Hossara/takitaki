import {createToaster} from "@meforma/vue-toaster/src/index"
import {defineNuxtPlugin} from "#app"

export default defineNuxtPlugin(() =>
{
    return {
        provide: {
            toast: createToaster({
                position: "top-left"
            })
        }
    }
})