<script lang="ts" setup>
const props = defineProps<{
    animation: any,
    height?: number | 200 | string,
    width?: number | 200 | string,
    speed?: number | 1
}>()

const animationData = useState(() => props.animation)
</script>

<template>
    <client-only>
        <Vue3Lottie
            :animationData="animationData"
            :height="width"
            :width="height"
            :speed="speed"
            class="relative scale-[200%] !m-0"
            :class="`w-[${width}px] h-[${height}px]`"
        />
    </client-only>
</template>