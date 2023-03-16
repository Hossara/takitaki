<script lang="ts" setup>

definePageMeta({ layout: "auth" })
useHead({ title: "TakiTaki - Login" })

const registerModal = new ModalHandler()

const toast = useNuxtApp().$toast  
const axios = useNuxtApp().$axios 

onMounted(() =>
{
    const query = useRoute().query
    
    switch(query["res"])
    {
        case "done":
            toast.success("Your account has been created successfully! Please activate your account before expiration and login!")
            break
        case "validate":
            const id = query["id"]
            const email = query["email"]

            if(id && email)
            {
                axios
                    .get(`/auth/validate/${email}/${id}`)
                    .then((value) => 
                    {
                        const data = value.data

                        if(data["status"] === "success")
                        {
                            location.href = "/?res=reg_done"
                        }
                        else if(data["code"] === "inv_usr")
                        {
                            toast.error("Registration link is not valid!")
                        }
                    })
            }
            else toast.error("Registration link is not valid!")
            break
        case "reg_done":
            toast.success("You registered successfully! ")
    }
})
</script>

<template>
    <div id="index" class="flex w-full justify-center">
        <ModalRegister :show="registerModal.status.value" @closeModal="registerModal.hide"/>

        <form method="post" class="pl-3 pt-3 pr-3 pb-4 shadow-md rounded-md bg-white max-w-[400px] w-full text-center">
            <TkInput class="w-full mb-4" placeholder="Email or phone number"/>

            <TkInput class="w-full mb-4" placeholder="Password" type="password"/>

            <TkButton class="mb-6 w-full">Login</TkButton>

            <NuxtLink to="/" class="hover:underline text-primary text-sm block mb-6">Forgot password?</NuxtLink>

            <hr class="mb-4">

            <TkButton type="button" class="w-full !bg-secondary" @click="registerModal.show">Create new account</TkButton>
        </form>
    </div>
</template>