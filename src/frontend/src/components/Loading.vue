<template>
  <v-overlay :value="appearance.loading" absolute>
    <v-progress-circular indeterminate size="64" />
  </v-overlay>
</template>

<script setup lang="ts">
import { useAppearanceStore } from '@/stores/appearance';

const appearance = useAppearanceStore();
</script>
