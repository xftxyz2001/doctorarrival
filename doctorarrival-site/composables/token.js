let cookieToken = null;

export const useToken = () => {
  if (cookieToken === null) {
    cookieToken = useCookie("token");
  }
  return cookieToken;
};
