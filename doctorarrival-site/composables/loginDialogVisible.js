let loginDialogVisible = null;

export const useLoginDialogVisible = () => {
  if (loginDialogVisible === null) {
    loginDialogVisible = ref(false);
  }
  return loginDialogVisible;
};
