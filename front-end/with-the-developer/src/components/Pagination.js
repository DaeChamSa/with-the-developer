import {ref, computed} from "vue";

export function usePagination(countItem, itemsPage){
    const currentPage = ref(1);
    const totalPage = computed(() => Math.ceil(countItem.length/itemsPage));

    const paginatedItems = computed(() => {
        const start = (currentPage.value-1) * itemsPage;
        const end = start + itemsPage;
        return countItem.slice(start, end);
    });

    const setPage = (page) => {
        if(page < 1 || page > totalPage) return;
        currentPage.value = page;
    };

    return {
        currentPage,
        totalPage,
        paginatedItems,
        setPage,
    };
}