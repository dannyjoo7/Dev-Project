export type Language = 'ko' | 'en';

export const translations = {
  ko: {
    // Header
    title: '시퀀스 다이어그램',
    import: '가져오기',
    export: '내보내기',
    exportSvg: 'SVG 내보내기',
    exportPng: 'PNG 내보내기',
    clear: '초기화',
    menu: '메뉴',
    menuDescription: '참여자 및 메시지 관리',

    // Default participants
    client: '클라이언트',
    server: '서버',
    database: '데이터베이스',

    // Default messages
    sendRequest: '요청 전송',
    queryData: '데이터 조회',
    returnData: '데이터 반환',
    sendResponse: '응답 전송',

    // Message Editor
    editMessage: '메시지 편집',
    addMessage: '메시지 추가',
    messageTypeSync: '동기 호출 (→)',
    messageTypeAsync: '비동기 호출 (⇢)',
    messageTypeReturn: '응답 (←)',
    messageTypeSelf: '자기 호출 (↻)',
    sender: '발신자',
    receiver: '수신자',
    messageType: '메시지 유형',
    messageContent: '메시지',
    messagePlaceholder: '메시지 내용',
    add: '추가',
    edit: '편집',
    update: '수정',
    cancel: '취소',
    messageList: '메시지 목록',
    moveUp: '위로',
    moveDown: '아래로',
    delete: '삭제',

    // Participant Editor
    participantManage: '참여자 관리',
    participantName: '이름',
    participantIcon: '아이콘 (선택)',
    participantNamePlaceholder: '참여자 이름',
    iconSelect: '아이콘 선택',
    iconChange: '아이콘 변경',
    iconNone: '아이콘 없음',
    addParticipant: '참여자 추가',
    save: '저장',

    // Icon labels
    iconUser: '사용자',
    iconServer: '서버',
    iconDatabase: '데이터베이스',
    iconGlobe: '웹',
    iconSmartphone: '모바일',
    iconMonitor: '데스크톱',
    iconCloud: '클라우드',
    iconCode: '코드',
    iconMail: '이메일',
    iconShoppingCart: '장바구니',
    iconCreditCard: '결제',
    iconFileText: '문서',
    iconSettings: '설정',

    // Alerts and confirmations
    importError: '파일을 불러오는데 실패했습니다.',
    confirmClearAll: '모든 내용을 삭제하시겠습니까?',

    // Language toggle
    language: '언어',
  },
  en: {
    // Header
    title: 'Sequence Diagram',
    import: 'Import',
    export: 'Export',
    exportSvg: 'Export SVG',
    exportPng: 'Export PNG',
    clear: 'Clear All',
    menu: 'Menu',
    menuDescription: 'Manage Participants & Messages',

    // Default participants
    client: 'Client',
    server: 'Server',
    database: 'Database',

    // Default messages
    sendRequest: 'Send Request',
    queryData: 'Query Data',
    returnData: 'Return Data',
    sendResponse: 'Send Response',

    // Message Editor
    editMessage: 'Edit Message',
    addMessage: 'Add Message',
    messageTypeSync: 'Synchronous (→)',
    messageTypeAsync: 'Asynchronous (⇢)',
    messageTypeReturn: 'Return (←)',
    messageTypeSelf: 'Self Call (↻)',
    sender: 'From',
    receiver: 'To',
    messageType: 'Message Type',
    messageContent: 'Message',
    messagePlaceholder: 'Message content',
    add: 'Add',
    edit: 'Edit',
    update: 'Update',
    cancel: 'Cancel',
    messageList: 'Message List',
    moveUp: 'Up',
    moveDown: 'Down',
    delete: 'Delete',

    // Participant Editor
    participantManage: 'Manage Participants',
    participantName: 'Name',
    participantIcon: 'Icon (Optional)',
    participantNamePlaceholder: 'Participant name',
    iconSelect: 'Select Icon',
    iconChange: 'Change Icon',
    iconNone: 'No Icon',
    addParticipant: 'Add Participant',
    save: 'Save',

    // Icon labels
    iconUser: 'User',
    iconServer: 'Server',
    iconDatabase: 'Database',
    iconGlobe: 'Web',
    iconSmartphone: 'Mobile',
    iconMonitor: 'Desktop',
    iconCloud: 'Cloud',
    iconCode: 'Code',
    iconMail: 'Email',
    iconShoppingCart: 'Shopping Cart',
    iconCreditCard: 'Payment',
    iconFileText: 'Document',
    iconSettings: 'Settings',

    // Alerts and confirmations
    importError: 'Failed to import file.',
    confirmClearAll: 'Are you sure you want to delete all items?',

    // Language toggle
    language: 'Language',
  },
};

export type TranslationKey = keyof typeof translations.ko;

export const getTranslation = (
  language: Language,
  key: TranslationKey,
): string => {
  return translations[language][key] || translations.en[key];
};
