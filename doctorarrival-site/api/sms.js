export function sendVerificationCode(phoneNumber) {
  return request({
    url: `/api/sms/send/code/${phoneNumber}`,
    method: "post"
  });
}
