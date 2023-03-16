<script setup lang="ts">
const props = defineProps<{
  show: boolean,
  title?: string
  closable?: boolean
  name?: string
}>()

const emit = defineEmits(["closeModal"])

const closeModal = () =>  { if (props.closable) emit("closeModal") }
</script>

<template>
  <client-only>
    <Teleport to="#modal">
      <div class="tkModal flex flex-wrap justify-center items-center opacity-0 -z-50 bg-black/40 fixed top-0 left-0 right-0 bottom-0 p-5 overflow-auto" 
      :class="{ '!opacity-100 !z-50' : show }" @click.self="closeModal">
        <div class="base flex flex-col max-w-[450px] w-full bg-white p-4 rounded-md shadow-md" :class="name">
          <div class="header flex justify-between items-center  border-b border-gray-300 pb-2 mb-3">
            <p class="text-lg">{{ title }}</p>

            <!--<img src="~/assets/img/icon/icon_exit_gray.svg" alt="x" @click="$emit('closeModal')">-->
            <span class="material-symbols-outlined p-2 !text-[20px] cursor-pointer select-none hover:bg-gray-50 rounded-full" @click="$emit('closeModal')">
                close
            </span>
          </div>

          <slot/>
        </div>
      </div>
    </Teleport>
  </client-only>
</template>