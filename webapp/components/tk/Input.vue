<script lang="ts" setup>
const props = defineProps<{
    placeholder?: string | undefined,
    type?: "text" | "email" | "password" | undefined,
    size?: "large" | "medium",
    required?: boolean,
    name?: string
}>()

const spanEl: Ref<null> | Ref<HTMLSpanElement> = ref(null)
const inputEl: Ref<null> | Ref<HTMLInputElement> = ref(null)

const changeVisibility = () => 
{
    const sel = (spanEl.value! as HTMLSpanElement)
    const iel = (inputEl.value! as HTMLInputElement)

    if(sel.dataset.status === "hide") 
    {
        iel.type = "text"
        sel.innerHTML = "visibility"
        sel.dataset.status = "show"
    }
    else
    {
        iel.type = "password"
        sel.innerHTML = "visibility_off"
        sel.dataset.status = "hide"
    }
}
</script>

<template>
    <div class="tk_input relative flex items-center justify-end">
        <input class="px-4 py-3 pr-[45px] rounded-md focus:!outline-primary border-2 w-full" 
            :type="type ? type : 'text'" :placeholder="placeholder" ref="inputEl" :required="required"
            :class="{ 'px-3 py-2': size === 'medium' }" :name="name">

            <span class="material-symbols-outlined absolute right-3 p-2 !text-[20px] hover:bg-gray-50 rounded-full cursor-pointer select-none"
                 v-if="type === 'password'" data-status="hide" @click="changeVisibility" ref="spanEl">
                visibility_off
            </span>
    </div>
</template>