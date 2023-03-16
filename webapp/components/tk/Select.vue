<script lang="ts" setup>
import { isArray } from '@vue/shared'

const props = defineProps<{
    placeholder?: string | undefined,
    list: any[]
    name?: string
}>()

const inputEl: Ref<null> | Ref<HTMLInputElement> = ref(null)
</script>

<template>
    <div class="tk_select flex items-center justify-end">
        <select v-if="!isArray(list)" :name="name"
            class="p-2 rounded-md focus:!outline-primary border-2 w-full">
            <option v-for="item in list" :key="item['key']" :value="item['value']">{{ item['key'] }}</option>
        </select>

        <select v-else :name="name" 
            class="p-2 rounded-md focus:!outline-primary border-2 w-full">
            <option v-for="item in list" :key="item">{{ item }}</option>
        </select>
    </div>
</template>