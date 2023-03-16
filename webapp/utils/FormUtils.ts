export const inputValue = (base: string, name: string, input: string = "input"): string => (
    document.querySelector(`${base} ${input}[name=${name}]`) as HTMLInputElement
).value

export const radioValue = (base: string, name: string): string => (
    document.querySelector(`${base} input[name=${name}]:checked`) as HTMLInputElement
).value

export const validateEmail = (email: string): boolean =>
    /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)