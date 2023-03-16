<script lang="ts" setup>

</script>

<template>
    <main data-main class="flex flex-col p-4 h-screen">
        <section class="flex justify-center items-center max-sm:h-[89vh] h-screen flex-wrap flex-col">
            <div class="flex p-2 lg:w-1/2 max-sm:w-full max-sm:justify-start sm:justify-start lg:max-w-none sm:max-w-[400px] max-sm:max-w-[400px] sm:w-full lg:h-full lg:justify-center lg:items-center max-sm:mb-5 sm:mb-5">
                <div class="flex flex-col">
                    <h1 class="font-bold text-[50px] text-primary">TakiTaki</h1>
                    <p class="text-[25px] max-sm:text-[20px]">Connect with friends and the world<br> around you on TakiTaki.</p>
                    <span class="text-[12px] text-gray-500">YasTech © 2023</span>
                </div>
            </div>
            <div class="lg:w-1/2 sm:w-full max-sm:w-full lg:h-full flex justify-center items-center">
                <slot/>
            </div>
        </section>
    </main>
</template>