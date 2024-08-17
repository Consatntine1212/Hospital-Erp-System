export class Messages {
    constructor(
        public senderId?: String,
        public senderEmail?: string,
        public recipientId?: number,
        public emailBody?: string,
        public emailSubject?: string,
        public isDraft: boolean = false,
        public recipientEmail?: string
    ) {}
}
