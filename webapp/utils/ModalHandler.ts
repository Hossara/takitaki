export class ModalHandler {
    status: Ref<boolean> = useState(() => false)
   
    toggle = () => this.status.value != this.status.value

    show = () => this.status.value = true

    hide = () => this.status.value = false
}