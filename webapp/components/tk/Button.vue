<script lang="ts" setup>
const props = defineProps<{
    type?: "button" | "submit",
    size?: "large" | "medium",
    disabled?: boolean
}>()
</script>

<template>
    <button :type="type"
    class="p-3 bg-primary text-white font-bold rounded-md text-xl hover:shadow-lg disabled:!shadow-none disabled:bg-primary/80"
    :class="{ 'p-2 text-[16px]': size === 'medium' }" :disabled="disabled">
        <slot/>
    </button>
</template>