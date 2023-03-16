<script lang="ts" setup>

defineProps<{
    name?: string,
    value?: string,
    size?: "large" | "medium",
    selected?: boolean
}>()

const inputEl: Ref<null> | Ref<HTMLInputElement> = ref(null)

const select = () => (inputEl.value! as HTMLInputElement).click()
</script>

<template>
    <div class="tk_radio flex items-center justify-end">

        <div class="flex items-center px-4 py-3 justify-between rounded-md focus:!outline-primary border-2 w-full cursor-pointer hover:shadow-sm"
        :class="{ 'px-3 py-2': size === 'medium' }" @click="select">
            <span class="text-[18px]" :class="{ 'text-[14.5px]': size === 'medium' }"><slot/></span>
            <input type="radio" :name="name" :value="value" :checked="selected" class="w-[15px]" ref="inputEl">
        </div>
    </div>
</template>