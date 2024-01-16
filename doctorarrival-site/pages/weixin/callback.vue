<script setup>
const route = useRoute();
const { token } = route.query;
if (window) {
  postMessage({ token }, location.origin);
}
useRouter().push("/");
</script>
