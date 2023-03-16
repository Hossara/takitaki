<script lang="ts" setup>
import Loader from "../../assets/animations/loading.json"
defineProps<{
  show: boolean
}>()

const axios = useNuxtApp().$axios 
const toast = useNuxtApp().$toast  

const years = range(1900, new Date().getFullYear())
const days = range(1, 31)
const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"]

const formRef = ref(null)
const isSubmit = useState(() => false)

const submit = () => 
{
    const form = (formRef.value! as HTMLFormElement)

    if(form.reportValidity())
    {
        let month = (months.indexOf(inputValue("#registerForm", "month", "select")) + 1).toString()
        let day = inputValue("#registerForm", "day", "select")
        let year = inputValue("#registerForm", "year", "select")
        
        if(month.length < 2) month = `0${month}`
        if(day.length < 2) day = `0${day}`

        const fYear = `${day}/${month}/${year}`
        
        const formData = new FormData()
        formData.append("firstname", inputValue("#registerForm", "firstname")) 
        formData.append("lastname", inputValue("#registerForm", "lastname")) 
        formData.append("email", inputValue("#registerForm", "email")) 
        formData.append("birthday", fYear) 
        formData.append("gender", radioValue("#registerForm", "gender")) 
        formData.append("password", inputValue("#registerForm", "password")) 

        const email = inputValue("#registerForm", "email")
        const password = inputValue("#registerForm", "password")

        if(password.length <= 3) toast.error("Password is too short!")
        else if (password.length > 50) toast.error("Password is too large!")
        else
        {
            if(!validateEmail(email)) toast.error("Email is not valid!")
            else
            {
                isSubmit.value = true
                    
                axios
                    .post("/auth/register", formData)
                    .then((value) => 
                    {
                        const data = value.data

                        if(data["status"] === "success")
                        {
                            location.href = "/?res=done"
                        }
                        else if(data["code"])
                        {
                            const code = data["code"]

                            switch(code)
                            {
                                case "too_req":
                                    toast.error("Your number of requests exceeded the limit. Please try again in 1 hour!")
                                    break
                                case "n_req":
                                    toast.warning(data["msg"])
                                    break
                                case "reg_d_ne":
                                    toast.warning("Error while sending registration email! Please contact admins or try again in hours.")
                                    break
                                case "us_exi":
                                    toast.warning("User already exists!")
                                    break
                            }
                        }
                    })    
            }
        }
    }
}
</script>

<template>
    <Modal name="register" :show="show" title="Register" @closeModal="$emit('closeModal')" :closable="!isSubmit">
        <form id="registerForm" method="POST" class="content flex flex-col" @submit.prevent="submit" ref="formRef">

            <div class="flex mb-3">
                <TkInput type="text" class="pr-1.5" placeholder="Firstname" size="medium" name="firstname" required/>
                <TkInput type="text" class="pl-1.5" placeholder="Lastname" size="medium" name="lastname" required/>
            </div>

            <TkInput type="email" placeholder="Email" size="medium" class="mb-3" name="email" required/>

            <TkInput type="password" placeholder="Password" name="password" size="medium" class="mb-3" required/>

            <span class="text-sm pb-1 pl-0.5">Birthday</span>

            <div class="flex mb-3">
                <TkSelect :list="months" class="w-1/3 pr-0.5" name="month"/>
                <TkSelect :list="days" class="w-1/3 pr-0.5 pl-0.5" name="day"/>
                <TkSelect :list="years.reverse()" class="w-1/3 pl-0.5" name="year"/>
            </div>

            <span class="text-sm pb-1 pl-0.5">Gender</span>

            <div class="flex mb-6">
                <TkRadio class="w-1/3 pr-0.5" name="gender" size="medium" value="FEMALE" selected>Female</TkRadio>
                <TkRadio class="w-1/3 pr-0.5 pl-0.5" name="gender" size="medium" value="MALE">Male</TkRadio>
                <TkRadio class="w-1/3 pl-0.5" name="gender" size="medium" value="OTHER">Other</TkRadio>
            </div>

            <p class="text-[12.5px] text-center mb-2">
                By clicking Sign Up, you agree to our 
                <NuxtLink to="/" class="underline text-primary">Terms</NuxtLink>, <NuxtLink to="/" class="underline text-primary">Privacy Policy</NuxtLink> and 
                <NuxtLink to="/" class="underline text-primary">Cookies Policy.</NuxtLink>
            </p>

            <!---->

            <TkButton 
                class="w-full !bg-secondary" size="medium" 
                :disabled="isSubmit" :class="{'disabled:!bg-primary' : isSubmit}">
                <span v-if="isSubmit" class="flex justify-center items-center">
                    <span>Creating</span>
                    <Animation :animation="Loader" :height="30" :width="30"/>
                </span>
                <span v-else>
                    Create new account
                </span>
            </TkButton>
        </form>
    </Modal>
</template>